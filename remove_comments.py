import os
import re

def remove_comments_from_java_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    original_content = content

    lines = content.split('\n')
    new_lines = []

    for line in lines:
        stripped = line.lstrip()
        if stripped.startswith('//'):
            continue
        if stripped.startswith('/*') or stripped.startswith('*'):
            continue
        new_lines.append(line)

    new_content = '\n'.join(new_lines)

    new_content = re.sub(r'/\*.*?\*/', '', new_content, flags=re.DOTALL)

    if new_content != original_content:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(new_content)
        return True
    return False

def main():
    src_dir = r'c:\Users\User\projekt-selenium\src'
    files_modified = 0

    for root, dirs, files in os.walk(src_dir):
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                if remove_comments_from_java_file(file_path):
                    files_modified += 1
                    print(f"Removed comments from: {file}")

    print(f"\nTotal files modified: {files_modified}")

if __name__ == '__main__':
    main()
